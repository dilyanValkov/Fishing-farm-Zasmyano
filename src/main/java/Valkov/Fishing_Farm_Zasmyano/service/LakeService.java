package Valkov.Fishing_Farm_Zasmyano.service;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.LakeInfoDTO;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Lake;
import org.springframework.stereotype.Service;


public interface LakeService {
    LakeInfoDTO mapLakeInfoDto();
}
