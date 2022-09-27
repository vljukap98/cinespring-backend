package hr.ljakovic.cinespring.service;


import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.model.RegToken;
import hr.ljakovic.cinespring.repo.RegTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegTokenService {

    @Autowired
    RegTokenRepo regTokenRepo;

    public RegToken getToken(UUID token) {
        return regTokenRepo.getById(token);
    }

    @Transactional
    public String confirmToken(UUID token) {
        RegToken confirmedRegToken = regTokenRepo.getById(token);

        if(confirmedRegToken.getConfirmed() != null) {
            throw new CineSpringException("Account already verified");
        }

        confirmedRegToken.setConfirmed(LocalDateTime.now());
        confirmedRegToken.getAppUser().setIsActivated(true);

        regTokenRepo.save(confirmedRegToken);

        return "Account email confirmed";
    }

    @Transactional
    public void saveToken(RegToken regToken) {
        regTokenRepo.save(regToken);
    }
}
