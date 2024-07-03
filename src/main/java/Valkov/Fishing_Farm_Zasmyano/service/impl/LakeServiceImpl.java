package Valkov.Fishing_Farm_Zasmyano.service.impl;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.LakeInfoDTO;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Lake;
import Valkov.Fishing_Farm_Zasmyano.repository.LakeRepository;
import Valkov.Fishing_Farm_Zasmyano.service.LakeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LakeServiceImpl implements LakeService {
    private final LakeRepository lakeRepository;
    private final ModelMapper modelMapper;

    @Override
    public LakeInfoDTO mapLakeInfoDto() {
        Lake lak1e = new Lake();
        this.lakeRepository.save(lak1e);
        Lake lake = this.lakeRepository.findById(1L).get();
        return this.modelMapper.map(lake, LakeInfoDTO.class);
    }

}
