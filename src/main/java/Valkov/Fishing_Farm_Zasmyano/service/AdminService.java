package Valkov.Fishing_Farm_Zasmyano.service;


public interface AdminService {

    void setUserRole(Long userId, String role);
    void setUserAttitude(Long userId, String attitude);
}
