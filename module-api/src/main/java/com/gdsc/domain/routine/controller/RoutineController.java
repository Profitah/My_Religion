package com.gdsc.domain.routine.controller;

import com.gdsc.domain.routine.entity.Routine;
import com.gdsc.domain.routine.model.RoutineRequest;
import com.gdsc.domain.routine.model.RoutineResponse;
import com.gdsc.domain.routine.model.RoutineStatusRequest;
import com.gdsc.domain.routine.service.RoutineService;
import com.gdsc.domain.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "루틴", description = "루틴 API")
@RestController
@RequestMapping("/api/v1/routine")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    @GetMapping("/{trackId}")
    public ResponseEntity<List<RoutineResponse>> findInitRoutineByTrack(@PathVariable(value = "trackId") Long trackId){
        List<Routine> routines = routineService.findByTrack(trackId);

        List<RoutineResponse> routineResponses = getRoutineResponses(routines);

        return ResponseEntity.ok(routineResponses);
    }

    @PostMapping("/{trackId}")
    public ResponseEntity<RoutineResponse> saveRoutine(@AuthenticationPrincipal User user,
                                                       @PathVariable(value = "trackId") Long trackId,
                                                       @RequestBody @Validated RoutineRequest routineRequest){
        Routine routine = routineService.save(routineRequest, trackId, user);

        return ResponseEntity.ok(RoutineResponse.of(routine));
    }

    @PatchMapping("/{routineId}")
    public ResponseEntity<RoutineResponse> updateRoutineStatus(@AuthenticationPrincipal User user,
                                                    @PathVariable(value = "routineId") Long routineId,
                                                    @RequestBody @Validated RoutineStatusRequest routineStatusRequest){
        Routine routine = routineService.updateStatus(routineStatusRequest, routineId, user);

        return ResponseEntity.ok(RoutineResponse.of(routine));
    }

    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deleteRoutine(@AuthenticationPrincipal User user,
                                              @PathVariable(value = "routineId") Long routineId){
        routineService.delete(routineId, user);

        return ResponseEntity.noContent().build();
    }

    private static List<RoutineResponse> getRoutineResponses(List<Routine> routines) {
        return routines.stream()
                .map(RoutineResponse::of)
                .toList();
    }
}
