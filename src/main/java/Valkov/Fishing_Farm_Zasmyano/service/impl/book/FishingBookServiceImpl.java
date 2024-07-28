package Valkov.Fishing_Farm_Zasmyano.service.impl.book;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing.BookFishingDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing.BookInfoFishingDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingReservation;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingSpot;
import Valkov.Fishing_Farm_Zasmyano.domain.model.user.User;
import Valkov.Fishing_Farm_Zasmyano.repository.fishing.FishingBookRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.fishing.FishingSpotRepository;
import Valkov.Fishing_Farm_Zasmyano.service.book.FishingBookService;
import Valkov.Fishing_Farm_Zasmyano.service.impl.EmailService;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserUtilService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FishingBookServiceImpl implements FishingBookService {

    private final UserUtilService userUtilService;
    private final FishingBookRepository fishingBookRepository;
    private final FishingSpotRepository fishingSpotRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    @Override
    public boolean book(BookFishingDto dto) {
        User user = userUtilService.getCurrentUser();
        Long number = dto.getFishingSpot().getNumber();
        Optional<FishingSpot> byId = fishingSpotRepository.findById(number);

        if (byId.isEmpty()){
            return false;
        }
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.UNCONFIRMED);
        statuses.add(Status.CONFIRMED);

        List<FishingReservation> existingReservations = fishingBookRepository.findByFishingSpot_IdAndEndDateAfterAndStartDateBeforeAndStatusIsIn(
                number, dto.getStartDate(), dto.getEndDate(),statuses);
        List<FishingReservation> conflicts = fishingBookRepository.findByFishingSpot_IdAndStartDateBetweenAndStatusIsIn(
                number, dto.getStartDate(), dto.getEndDate(),statuses);

        if (!existingReservations.isEmpty() || !conflicts.isEmpty()){
            return false;
        }

        FishingSpot fishingSpot = byId.get();

        FishingReservation reservation = modelMapper.map(dto, FishingReservation.class);

        reservation.setStatus(Status.UNCONFIRMED);
        reservation.setUser(user);
        reservation.setFishingSpot(fishingSpot);
        reservation.calculateTotalPrice();
        fishingBookRepository.save(reservation);
        emailService.sendSimpleEmail(user.getEmail(),
                reservation.emailContent(),
                reservation.statusMessage(reservation.getStatus().toString()));

        return true;
    }

    @Override
    public List<BookInfoFishingDto> getAllUserBookings() {
        User user = userUtilService.getCurrentUser();
        String email = user.getEmail();
        List<FishingReservation> allByEmail = this.fishingBookRepository.findAllByEmail(email);
        List<BookInfoFishingDto> dtos = new ArrayList<>();
        for (FishingReservation reservation : allByEmail) {
            BookInfoFishingDto mapped = modelMapper.map(reservation, BookInfoFishingDto.class);
            mapped.setSpotNumber(reservation.getFishingSpot().getId());
            mapped.setUserInfo(reservation.getUser().getUserInfo());
            mapped.setReservationNumber(reservation.getId());
            dtos.add(mapped);
        }
        return dtos;
    }

    @Override
    public void deleteReservations(Long userId) {
        fishingBookRepository.deleteAllByUser_id(userId);
    }

    @Override
    public boolean isFishingSpotHasCapacity(BookFishingDto dto) {
       FishingSpot fishingSpot = fishingSpotRepository.findById(dto.getFishingSpot().getNumber()).get();
       return dto.getFishermanCount() > fishingSpot.getCapacity();
    }

    @Override
    public List<BookInfoFishingDto> getAllBookings() {

        List<FishingReservation> bookings = fishingBookRepository.findAll();
        List<BookInfoFishingDto> dtos = new ArrayList<>();

        for (FishingReservation booking : bookings) {
            BookInfoFishingDto mapped = modelMapper.map(booking, BookInfoFishingDto.class);
            mapped.setUserInfo(booking.getUser().getUserInfo());
            mapped.setReservationNumber(booking.getId());
            mapped.setSpotNumber(booking.getFishingSpot().getId());
            dtos.add(mapped);
        }
        return dtos;
    }

}
