package com.zingpay.commission.calculation.entity;

import com.zingpay.commission.calculation.dto.AppUserDto;
import com.zingpay.commission.calculation.util.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bilal Hassan on 01-Feb-21
 * @project commission-calculation-microservice
 */

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class AppUser {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int accountId;
    @Column(name = "parent_id")
    private int parentId;
    @Column(name = "branch_id")
    private int branchId;
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "account_status_id")
    private int accountStatusId;
    @Column(name = "account_type_id")
    private int accountTypeId;
    @Column(name = "service_type_id")
    private int serviceTypeId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "address")
    private String address;
    @Column(name = "cell_phone")
    private String cellPhone;
    @Column(name = "email")
    private String email;
    @Column(name = "cnic_number")
    private String cnicNumber;
    @Column(name = "alternate_cell_phone")
    private String alternateCellPhone;
    @Column(name = "last_login")
    private long lastLogin;
    @Column(name = "created_date_time")
    private long createDateTime;
    @Column(name = "created_by")
    private int createdBy;
    @Column(name = "modified_date_time")
    private long modifiedDateTime;
    @Column(name = "modified_by")
    private int modifiedBy;
    @Column(name = "suspend_date_time")
    private long suspendDateTime;
    @Column(name = "t_pin")
    private String tPin;
    @Column(name = "login_failed_count")
    private int loginFailedCount;
    @Column(name = "is_generated_t_pin")
    private boolean isGeneratedTpin;
    @Column(name = "pin")
    private String pin;
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @Column(name = "house_type_id")
    private int houseTypeId;
    @Column(name = "deposit_type_id")
    private int depositTypeId;
    @Column(name = "business_name")
    private String businessName;
    @Column(name = "mobile_location")
    private String mobileLocation;
    @Column(name = "cnic_issue_date")
    private long cnicIssueDate;
    @Column(name = "cnic_front")
    private byte[] cnicFront;
    @Column(name = "cnic_back")
    private byte[] cnicBack;
    @Column(name = "other_attachment")
    private byte[] otherAttachment;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "transaction_date")
    private long transactionDate;
    @Column(name = "transaction_amount")
    private long transactionAmount;
    @Column(name = "house_number")
    private String houseNumber;
    @Column(name = "lat")
    private String lat;
    @Column(name = "lng")
    private String lng;
    @Column(name = "m_pin")
    private String mPin;
    @Column(name = "kyc_status_id")
    private int kycStatusId;
    @Column(name = "kyc_description")
    private String kycDescription;
    @Column(name = "device_id")
    private String deviceId;

    public static AppUserDto convertToDto(AppUser appUser) {
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setAccountId(appUser.getAccountId());

        if(appUser.getAccountStatusId() == 1) {
            appUserDto.setAccountStatus(AccountStatus.ACTIVE);
        } else if(appUser.getAccountStatusId() == 2) {
            appUserDto.setAccountStatus(AccountStatus.PENDING);
        } else if(appUser.getAccountStatusId() == 3) {
            appUserDto.setAccountStatus(AccountStatus.SUSPEND);
        } else if(appUser.getAccountStatusId() == 4) {
            appUserDto.setAccountStatus(AccountStatus.INACTIVE);
        }

        if(appUser.getAccountTypeId() == 1) {
            appUserDto.setAccountType(AccountType.VIRTUAL_AGENT);
        } else if(appUser.getAccountTypeId() == 2) {
            appUserDto.setAccountType(AccountType.RETAILER);
        } else if(appUser.getAccountTypeId() == 3) {
            appUserDto.setAccountType(AccountType.SUPER_SUPER_REP);
        } else if(appUser.getAccountTypeId() == 4) {
            appUserDto.setAccountType(AccountType.SUPER_REP);
        } else if(appUser.getAccountTypeId() == 5) {
            appUserDto.setAccountType(AccountType.REP);
        } else if(appUser.getAccountTypeId() == 6) {
            appUserDto.setAccountType(AccountType.CONSUMER);
        }

        if(appUser.getServiceTypeId() == 1) {
            appUserDto.setServiceType(ServiceType.HOUSE);
        } else if(appUser.getServiceTypeId() == 2) {
            appUserDto.setServiceType(ServiceType.SHOP);
        }

        appUserDto.setAddress(appUser.getAddress());
        appUserDto.setAlternateCellPhone(appUser.getAlternateCellPhone());
        appUserDto.setBranchId(appUser.getBranchId());
        appUserDto.setCellPhone(appUser.getCellPhone());
        appUserDto.setCnicNumber(appUser.getCnicNumber());
        appUserDto.setCreateDateTime(appUser.getCreateDateTime());
        appUserDto.setCreatedBy(appUser.getCreatedBy());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setFullName(appUser.getFullName());
        appUserDto.setGeneratedTpin(appUser.isGeneratedTpin());
        appUserDto.setGroupId(appUser.getGroupId());
        appUserDto.setLastLogin(appUser.getLastLogin());
        appUserDto.setLoginFailedCount(appUser.getLoginFailedCount());
        appUserDto.setModifiedBy(appUser.getModifiedBy());
        appUserDto.setModifiedDateTime(appUser.getModifiedDateTime());
        appUserDto.setParentId(appUser.getParentId());
        //appUserDto.setPassword(appUser.getPassword());
        //appUserDto.setTPin(appUser.getTPin());
        appUserDto.setSuspendDateTime(appUser.getSuspendDateTime());
        appUserDto.setUsername(appUser.getUsername());
        //appUserDto.setSmsPin(appUser.getSmsPin());
        //appUserDto.setEmailPin(appUser.getEmailPin());
        if(appUser.getProfilePicture() != null) {
            appUserDto.setProfilePicture(new String(appUser.getProfilePicture()));
        }

        if(appUser.getHouseTypeId() == 1) {
            appUserDto.setHouseType(HouseType.RENT);
        } else if(appUser.getHouseTypeId() == 2) {
            appUserDto.setHouseType(HouseType.OWN);
        }

        if(appUser.getDepositTypeId() == 1) {
            appUserDto.setDepositType(DepositType.BANK_DEPOSIT);
        } else if(appUser.getDepositTypeId() == 2) {
            appUserDto.setDepositType(DepositType.BANK_TRANSFER);
        } else if(appUser.getDepositTypeId() == 3) {
            appUserDto.setDepositType(DepositType.EASY_PAISA);
        }

        appUserDto.setBusinessName(appUser.getBusinessName());
        appUserDto.setMobileLocation(appUser.getMobileLocation());
        appUserDto.setCnicIssueDate(appUser.getCnicIssueDate());

        if(appUser.getCnicFront() != null) {
            appUserDto.setCnicFront(new String(appUser.getCnicFront()));
        }
        if(appUser.getCnicBack() != null) {
            appUserDto.setCnicBack(new String(appUser.getCnicBack()));
        }
        if(appUser.getOtherAttachment() != null) {
            appUserDto.setOtherAttachment(new String(appUser.getOtherAttachment()));
        }

        appUserDto.setTransactionId(appUser.getTransactionId());
        appUserDto.setTransactionDate(appUser.getTransactionDate());
        appUserDto.setTransactionAmount(appUser.getTransactionAmount());
        appUserDto.setHouseNumber(appUser.getHouseNumber());

        appUserDto.setLat(appUser.getLat());
        appUserDto.setLng(appUser.getLng());

        //appUserDto.setMPin(appUser.getMPin());

        if(appUser.getKycStatusId() == 1) {
            appUserDto.setKycStatus(KycStatus.PENDING);
        } else if(appUser.getKycStatusId() == 2) {
            appUserDto.setKycStatus(KycStatus.SUBMIT);
        } else if(appUser.getKycStatusId() == 3){
            appUserDto.setKycStatus(KycStatus.APPROVE);
        } else if(appUser.getKycStatusId() == 4){
            appUserDto.setKycStatus(KycStatus.REJECT);
        }

        appUserDto.setKycDescription(appUser.getKycDescription());
        appUserDto.setDeviceId(appUser.getDeviceId());

        return appUserDto;
    }

    public static List<AppUserDto> convertToDto(List<AppUser> appUsers) {
        List<AppUserDto> appUserDtos = new ArrayList<AppUserDto>();
        appUsers.forEach(appUser -> {
            appUserDtos.add(convertToDto(appUser));
        });
        return appUserDtos;
    }
}
