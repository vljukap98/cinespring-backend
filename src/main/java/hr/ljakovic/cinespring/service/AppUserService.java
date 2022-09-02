package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.model.AppUser;
import hr.ljakovic.cinespring.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    AppUserRepo appUserRepo;

    public AppUser getAppUserData(String username) {
        return appUserRepo.findByUsername(username)
                .orElseThrow(() -> new CineSpringException("User not found"));
    }
}
