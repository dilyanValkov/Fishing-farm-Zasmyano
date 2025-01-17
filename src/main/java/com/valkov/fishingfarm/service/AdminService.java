package com.valkov.fishingfarm.service;

public interface AdminService {

    void setUserRole(Long userId, String role);

    void setUserAttitude(Long userId, String attitude);

    void setBookBungalowStatus(Long id, String status);

    void deleteBookBungalowById(Long id);

    void deleteBookFishingById(Long id);

    void setBookFishingStatus(Long id, String status);
}
