package Valkov.Fishing_Farm_Zasmyano.service.impl;

import Valkov.Fishing_Farm_Zasmyano.config.UserSession;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Bungalow;
import Valkov.Fishing_Farm_Zasmyano.domain.model.BungalowReservation;
import Valkov.Fishing_Farm_Zasmyano.domain.model.User;
import Valkov.Fishing_Farm_Zasmyano.repository.UserRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.bungalow.BungalowBookingRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.bungalow.BungalowRepository;
import Valkov.Fishing_Farm_Zasmyano.service.BungalowBookingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BungalowBookingServiceImpl implements BungalowBookingService {
    private final BungalowBookingRepository bungalowBookingRepository;
    private final BungalowRepository bungalowRepository;
    private final ModelMapper modelMapper;
    private final UserSession userSession;
    private final UserRepository userRepository;

    public BungalowBookingServiceImpl(BungalowBookingRepository bungalowBookingRepository, BungalowRepository bungalowRepository, ModelMapper modelMapper, UserSession userSession, UserRepository userRepository) {
        this.bungalowBookingRepository = bungalowBookingRepository;
        this.bungalowRepository = bungalowRepository;
        this.modelMapper = modelMapper;
        this.userSession = userSession;
        this.userRepository = userRepository;
    }
    @Override
    public List<Bungalow> allBungalows(){
        return bungalowRepository.findAll();
    }
    @Override
    public String book(BookBungalowDto dto) {
        User user = this.userRepository.getByEmail(userSession.getEmail());

        Optional<Bungalow> byId = bungalowRepository.findById(dto.getNumber());
        if (byId.isEmpty()){
            return "Бунгало с номер " + dto.getNumber();
        }

        List<BungalowReservation> existingReservations = bungalowBookingRepository
                .findByBungalowIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        dto.getNumber(), dto.getStartDate(), dto.getEndDate());
        if (!existingReservations.isEmpty()){
            return "Бунгалото е резервирано за посочените дати.";
        }
        Bungalow bungalow = byId.get();

        BungalowReservation reservation = modelMapper.map(dto, BungalowReservation.class);
        reservation.setStatus(Status.PENDING);
        reservation.setUser(user);
        reservation.setBungalow(bungalow);
        reservation.calculateTotalPrice();
        bungalowBookingRepository.save(reservation);
        return "Успешна резвервация.";
    }
}