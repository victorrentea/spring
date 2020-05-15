package com.example.demo.security;

import com.example.demo.entity.Training;
import com.example.demo.repo.TrainingRepo;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Override

    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission) {
        throw new NotImplementedException();
    }


    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        if ("TRAINING".equals(targetType) && "UPDATE".equals(permission)) {

            DemoPrincipal principal = (DemoPrincipal) auth.getPrincipal();
            Training training = trainingRepo.findById((Long) targetId).get();

            return principal.getManagedTeacherIds().contains(training.getTeacher().getId());
        } else {
            throw new NotImplementedException();
        }
    }

    @Autowired
    private TrainingRepo trainingRepo;
}