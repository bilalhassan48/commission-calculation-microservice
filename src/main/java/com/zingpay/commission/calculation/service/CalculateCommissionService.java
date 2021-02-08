package com.zingpay.commission.calculation.service;

import com.zingpay.commission.calculation.dto.*;
import com.zingpay.commission.calculation.entity.AppUser;
import com.zingpay.commission.calculation.entity.Transaction;
import com.zingpay.commission.calculation.repository.AppUserRepository;
import com.zingpay.commission.calculation.repository.TransactionRepository;
import com.zingpay.commission.calculation.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author Bilal Hassan on 12-Jan-21
 * @project commission-calculation-microservice
 */

@Service
public class CalculateCommissionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    private static final String ZINGPAY_USER = "ZINGPAY";
    private static final String SYSTEM_USER = "SYSTEM";

    /*public List<TransactionDto> calculateCommission(TransactionCommissionDto transactionCommissionDto) {
        List<TransactionDto> transactionDtos = new ArrayList<TransactionDto>();
        List<CalculateCommissionDto> calculateCommissionDtos = transactionCommissionDto.getCalculateCommissionDtos();
        TransactionDto transactionDto = transactionCommissionDto.getTransactionDto();
        List<AppUserDtoForCommission> appUserDtoForCommissions = transactionCommissionDto.getAppUserDtoForCommissions();

        Stream<CalculateCommissionDto> calculateCommissionDtoStream = calculateCommissionDtos.stream().filter(calculateCommissionDto -> calculateCommissionDto.getFeeGroupName().contains("DEFAULT"));
        Optional<CalculateCommissionDto> calculationCommissionOptional = calculateCommissionDtoStream.findFirst();

        int totalFeePercentage = calculationCommissionOptional.get().getFee();

        double totalCommissionAmount = 0;
        if(calculationCommissionOptional.get().getFeeType() == 1) {
            totalCommissionAmount = totalFeePercentage;
        } else {
            totalCommissionAmount = totalFeePercentage * transactionDto.getAmount() / 100;
        }

        double zingpayCommissionPercent = calculateZingpayCommissionPercentage(calculateCommissionDtos, appUserDtoForCommissions);

        for (CalculateCommissionDto calculateCommissionDto : calculateCommissionDtos) {
            for (AppUserDtoForCommission appUserDtoForCommission : appUserDtoForCommissions) {
                if(calculateCommissionDto.getFeeGroupName().endsWith(appUserDtoForCommission.getGroupName())) {
                    TransactionDto transactionDtoToAdd = new TransactionDto();
                    double commissionAmount = 0;
                    if(!calculateCommissionDto.getFeeGroupName().contains("ZINGPAY")) {
                        commissionAmount = totalCommissionAmount * calculateCommissionDto.getFee() / 100;
                        transactionDtoToAdd.setDescription("COMMISSION calculated for " + appUserDtoForCommission.getUsername());
                        transactionDtoToAdd.setRefTo(appUserDtoForCommission.getUsername());
                    } else {
                        commissionAmount = totalCommissionAmount * zingpayCommissionPercent / 100;
                        transactionDtoToAdd.setDescription("COMMISSION calculated for ZINGPAY");
                        transactionDtoToAdd.setRefTo("zingpay");
                    }

                    if(commissionAmount > 0) {
                        transactionDtoToAdd.setAccountId(appUserDtoForCommission.getAccountId());
                        transactionDtoToAdd.setAmount(commissionAmount);
                        transactionDtoToAdd.setZingpayTransactionType(ZingpayTransactionType.TX_COMMISSION);
                        transactionDtoToAdd.setTransactionType(TransactionType.CREDIT);
                        transactionDtoToAdd.setTransactionStatus(TransactionStatus.SUCCESS);
                        transactionDtoToAdd.setChannelType(transactionDto.getChannelType());
                        transactionDtoToAdd.setBillingMonth(transactionDto.getBillingMonth());
                        transactionDtoToAdd.setBundleId(transactionDto.getBundleId());
                        transactionDtoToAdd.setDateTime(transactionDto.getDateTime());
                        transactionDtoToAdd.setRefFrom(transactionDto.getRefFrom());
                        transactionDtoToAdd.setRetailerRefNumber(transactionDto.getId() + "");
                        transactionDtoToAdd.setServiceId(transactionDto.getServiceId());
                        transactionDtoToAdd.setServiceProvider(transactionDto.getServiceProvider());

                        transactionDtos.add(transactionDtoToAdd);
                    }
                }
            }
        }

        return transactionDtos;
    }

    private double calculateZingpayCommissionPercentage(List<CalculateCommissionDto> calculateCommissionDtos, List<AppUserDtoForCommission> appUserDtoForCommissions) {
        double zingpayCommissionPercent = 100;

        for (CalculateCommissionDto calculateCommissionDto : calculateCommissionDtos) {
            *//*if(!calculateCommissionDto.getFeeGroupName().contains("DEFAULT") && !calculateCommissionDto.getFeeGroupName().contains("ZINGPAY")) {
                zingpayCommissionPercent = zingpayCommissionPercent - calculateCommissionDto.getFee();
            }*//*
            for(AppUserDtoForCommission appUserDtoForCommission : appUserDtoForCommissions) {
                if(calculateCommissionDto.getFeeGroupName().endsWith(appUserDtoForCommission.getGroupName()) && !calculateCommissionDto.getFeeGroupName().contains("ZINGPAY")) {
                    zingpayCommissionPercent = zingpayCommissionPercent - calculateCommissionDto.getFee();
                }
            }
        }
        return zingpayCommissionPercent;
    }*/

    public List<TransactionDto> calculateCommission(CommissionDto commissionDto) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(commissionDto.getAccountId());
        AppUser appUser = new AppUser();
        if(appUserOptional.isPresent()) {
            appUser = appUserOptional.get();
        }
        Optional<Transaction> transactionOptional = transactionRepository.findById(commissionDto.getTransactionId());
        Transaction transaction = new Transaction();
        if(transactionOptional.isPresent()) {
            transaction = transactionOptional.get();
        }

        //AppUser zingpayUser = appUserRepository.findByUsername(ZINGPAY_USER);
        AppUser systemUser = appUserRepository.findByUsername(SYSTEM_USER);

        List<Transaction> transactions = calculateFees(appUser, systemUser, commissionDto.getServiceId(), transaction);
        transactionRepository.saveAll(transactions);
        return Transaction.convertToDto(transactions);
    }

    public List<Transaction> calculateFees(AppUser appUser, AppUser systemUser, long serviceId, Transaction transaction) {
        // List<UserAccount> allUsers = userService.getAllParents(user.getAccountId());
        Map<String, AppUserDto> users = getUsersData(appUser, systemUser, serviceId);
        AppUserDto systemUserDto = users.get(SYSTEM_USER);
        AppUserDto zingpayUserDTO = users.get(ZINGPAY_USER);
        calculateValuesFromParent(zingpayUserDTO, systemUserDto, transaction);
        spreadDownFees(zingpayUserDTO);
        //printUsers(topMostUserDTO);
        return createTransactions(zingpayUserDTO, transaction);
    }

    /**
     * A method to load all the data of the users including Fees.
     *
     * @param appUser Transactional user who performed the transaction.
     * @param systemUser System user which has the default fee price.
     * @param serviceId Service id purchased by the transactional user.
     * @return Map A map containing two entries. One is the top most user of the
     * hierarchy of the transactional user which shall have its share of the
     * purchase. The other entry is the SYSTEM user in the form of a DTO.
     * @author ambreen
     */
    private Map<String, AppUserDto> getUsersData(AppUser appUser, AppUser systemUser, long serviceId) {
        // result map
        Map<String, AppUserDto> resultMap = new HashMap<>();

        // map and list for easy iterations
        Map<Integer, AppUserDto> usersMap = new HashMap<>();
        List<Integer> accountIds = new ArrayList<>();

        // create DTO for the SYSTEM user first
        AppUserDto systemUserDTO = AppUser.convertToDto(systemUser);
        // add system user to a map for returning it to the caller
        resultMap.put(SYSTEM_USER, systemUserDTO);
        // add it for easy iteration later for fetching fees
        usersMap.put(systemUserDTO.getAccountId(), systemUserDTO);
        accountIds.add(systemUserDTO.getAccountId());

        // this variable will hold the highest user at any specific time
        // during the iteration.
        AppUserDto zingpayUserDTO = null;
        AppUser u = appUser;
        // loop untill there is no valid account. 0 means
        // user was processed in previous iteration.
        while (u != null && u.getAccountId() != 0) {
            // create user DTO object
            AppUserDto userDTO = AppUser.convertToDto(u);

            if (zingpayUserDTO == null) {
                zingpayUserDTO = userDTO;
            } else {
                zingpayUserDTO.setParentId(userDTO.getParentId());
                userDTO.setChild(zingpayUserDTO);
                zingpayUserDTO = userDTO;
            }
            // if parent exists, iterate over the parent
            if (u.getParentId() != 0) {
                System.out.println("finding parent with id " + u.getParentId());
                Optional<AppUser> uOptional = appUserRepository.findById(u.getParentId());
                if(uOptional.isPresent()) {
                    u = uOptional.get();
                }
            } else {
                u = null;
            }

            // just to keep track of all objects for later iteration
            usersMap.put(userDTO.getAccountId(), userDTO);
            accountIds.add(userDTO.getAccountId());
        }
        // we have the top most user now. Add it for returning it to caller.
        resultMap.put(ZINGPAY_USER, zingpayUserDTO);

        /*
         * Now here, we have all the users in the usersMap Map
         * and accountIds List. Next query shall bring all the fees of these
         * users in one go. A possible optimization is to skip fees with zero
         * value.
         */

        // load all the fees into all user dto objects
        if (zingpayUserDTO != null && accountIds != null && !accountIds.isEmpty()) {

            System.out.println("finding Fees for users in List" + accountIds);

            // Bring the data.
            Map<Integer, AppUserDto> usersWithFees = getDefaultFeesByIds(accountIds, serviceId);

            if (usersWithFees != null && !usersWithFees.isEmpty()) {
                System.out.println("Map returned User objects" + usersWithFees.size());

                Iterator<Integer> i = usersMap.keySet().iterator();
                while (i.hasNext()) {
                    Integer accountId = i.next();
                    System.out.println("Assigning fees to user with accountId " + accountId);
                    AppUserDto value = usersMap.get(accountId);
                    System.out.println("No. of fees assigned : " +  usersWithFees.get(accountId).getFees());
                    // attach fees for each user.
                    value.setFees(usersWithFees.get(accountId).getFees());
                }
            }
        }
        return resultMap;
    }

    private Map<Integer, AppUserDto> getDefaultFeesByIds(List<Integer> accountIds, long serviceId) {

        Map<Integer, AppUserDto> userFeesMap = null;
        /*Map<Object, Object> params = new HashMap<>();
        params.put("accountIdList", accountIds);
        params.put("serviceId", serviceId);

        System.out.println("Retrieving fees for accounts and service " + new Object[]{accountIds, serviceId});

        List result = dataService.findWithNamedQuery("Fee.findDefaultFeesForMultipleAccounts", params);*/
        List<Object> result = transactionRepository.findDefaultFeesForMultipleAccounts(accountIds, serviceId);

        if (result != null && !result.isEmpty()) {
            userFeesMap = new HashMap<>();
            for (Object row : result) {
                Object[] rowValues = (Object[]) row;
                Integer accountId = (Integer) rowValues[0];

                String userName = (String) rowValues[1];

                String feeType = String.valueOf(rowValues[2]);
                Double fee = (Double) rowValues[3];
                //String feeTypeId = String.valueOf(((RefFeeType) rowValues[4]).getFeeTypeId());
                String feeTypeId = String.valueOf(rowValues[4]);
                String feeName = String.valueOf(rowValues[5]);

                FeeDTO feeDTO = new FeeDTO(feeName, fee, Integer.parseInt(feeTypeId), feeType, "CREDIT");

                AppUserDto userDTO = userFeesMap.get(accountId);

                if (userDTO == null) {
                    Optional<AppUser> appUserOptional = appUserRepository.findById(accountId);
                    AppUser appUser = new AppUser();
                    if(appUserOptional.isPresent()) {
                        appUser = appUserOptional.get();
                    }
                    userDTO = AppUser.convertToDto(appUser);
                    userFeesMap.put(accountId, userDTO);
                    Map<String, FeeDTO> feesMap = new HashMap<>();
                    feesMap.put(feeDTO.getFeeName(), feeDTO);
                    userDTO.setFees(feesMap);
                } else {
                    Map<String, FeeDTO> feesMap = userDTO.getFees();
                    if (feesMap == null) {
                        feesMap = new HashMap<>();
                        feesMap.put(feeDTO.getFeeName(), feeDTO);
                        userDTO.setFees(feesMap);
                    }
                    userDTO.getFees().put(feeDTO.getFeeName(), feeDTO);
                }
            }
        }
        return userFeesMap;
    }

    private List<Transaction> createTransactions(AppUserDto topmostUser, Transaction transaction) {
        List<Transaction> transactions = new ArrayList<>();
        AppUserDto user = topmostUser;
        while (user != null) {
            Collection<FeeDTO> fees = user.getFees().values();
            for (FeeDTO fee : fees) {
                if (fee.getCalculatedValue() != 0) {
                    Transaction t = createTransaction(user, fee, transaction);
                    if (t != null) {
                        transactions.add(t);
                    }
                }
            }
            user = user.getChild();
        }
        return transactions;
    }

    private Transaction createTransaction(AppUserDto user, FeeDTO fee, Transaction transaction) {
        Transaction t = null;
        if (fee.getCalculatedValue() != 0) {

            t = new Transaction();
            Optional<AppUser> accountOptional = appUserRepository.findById(user.getAccountId());//dataService.find(AppUser.class, user.getAccountId());
            AppUser account = new AppUser();

            if(accountOptional.isPresent()) {
                account = accountOptional.get();
            }

            t.setTransactionTypeId(TransactionType.CREDIT.getId());
            t.setTransactionStatusId(TransactionStatus.SUCCESS.getId());
            t.setZingpayTransactionTypeId(ZingpayTransactionType.TX_COMMISSION.getId());
            t.setAmount(fee.getCalculatedValue());
            t.setDateTime(new Date());
            t.setAccountId(account.getAccountId());
            t.setRefFrom("ZINGPAY");
            t.setRefTo(account.getUsername());
            t.setServiceId(transaction.getServiceId());
            t.setRetailerRefNumber(String.valueOf(transaction.getId()));
            t.setServiceProvider(transaction.getServiceProvider());
            t.setDescription(fee.getFeeName() + " calculated for " + account.getUsername());
        }
        return t;
    }

    private void calculateValuesFromParent(final AppUserDto zingpayUserDto, final AppUserDto systemUserDto, final Transaction transaction) {
        if (zingpayUserDto != null && systemUserDto != null) {
            if (systemUserDto.getFees() != null && !systemUserDto.getFees().isEmpty()) {
                Iterator<String> i = systemUserDto.getFees().keySet().iterator();
                while (i.hasNext()) {
                    String key = i.next();
                    FeeDTO fee = systemUserDto.getFees().get(key);
                    if (fee != null && fee.getValue() != 0) {
                        FeeDTO userFee = zingpayUserDto.getFees().get(key);

                        // fee changed from double to big decimal to resolve fraction issue
                        //Double value = fee.getValue();
                        BigDecimal value = new BigDecimal(fee.getValue());
                        if (FeeType.VARIABLE.getId() == fee.getFeeTypeId()) {
                            BigDecimal amount = new BigDecimal(transaction.getAmount());
                            BigDecimal calcuateValue = amount.multiply(value.divide(new BigDecimal(100)));
                            calcuateValue = calcuateValue.setScale(2, RoundingMode.DOWN);
                            //Double calculateValue = Double.valueOf(transaction.getAmount()) * (value / Constants.HUNDRED);
                            //fee.setCalculatedValue(calculateValue);

                            BigDecimal userFeeValue = new BigDecimal(userFee.getValue());
                            BigDecimal calcuateValue1 = calcuateValue.multiply(userFeeValue.divide(new BigDecimal(100)));
                            calcuateValue1 = calcuateValue1.setScale(2, RoundingMode.DOWN);


                            userFee.setCalculatedValue(calcuateValue1.doubleValue());
                        } else if (FeeType.ABSOLUTE.getId() == fee.getFeeTypeId()) {
                            fee.setCalculatedValue(value.doubleValue());
                            BigDecimal userFeeValue = new BigDecimal(userFee.getValue());
                            BigDecimal calcuateValue1 = value.multiply(userFeeValue.divide(new BigDecimal(100)));
                            calcuateValue1 = calcuateValue1.setScale(2, RoundingMode.DOWN);

                            userFee.setCalculatedValue(calcuateValue1.doubleValue());
                        }
                    }
                }
            }
        }
    }

    private void spreadDownFees(AppUserDto topMostParentUser) {
        if(topMostParentUser.getFees() != null) {
            Iterator<String> feesIt = topMostParentUser.getFees().keySet().iterator();
            while (feesIt.hasNext()) {
                FeeDTO fee = topMostParentUser.getFees().get(feesIt.next());
                System.out.println("Spreading down fee of type " + fee.getFeeTypeName());
                if (fee.getCalculatedValue() > 0) {
                    calculateFee(topMostParentUser.getChild(), topMostParentUser, fee);
                } else {
                    System.out.println("Skipping fee as value is " + new Object[]{fee.getFeeTypeName(), fee.getCalculatedValue()});
                }
            }
        }
    }

    private static void calculateFee(AppUserDto user, AppUserDto parent, FeeDTO parentUserFee) {
        if (user != null) {
            FeeDTO userFee = user.getFees().get(parentUserFee.getFeeName());
            // if no fee or fee is zero, call the same method with current
            // user's child as the user skipping the current user.
            if (userFee == null || userFee.getValue() == 0) {
                calculateFee(user.getChild(), parent, parentUserFee);
            } else {
                // get the fee. this would be in percentages
                Double value = userFee.getValue();
                // get the parent calculated fee
                Double parentCalculatedValue = parentUserFee.getCalculatedValue();
                // child fee is the percentage of the parent. so calculate child fee here
                Double userCalculatedValue = parentCalculatedValue * (value / Double.valueOf(100));
                // round it as per application requirement and set it in the DTO
                userCalculatedValue = Double.valueOf(Utils.formatAmount(String.valueOf(userCalculatedValue)));
                userFee.setCalculatedValue(userCalculatedValue);
                // change the parent fee. parent new fee is now (parent old fee - child fee)
                parentUserFee.setCalculatedValue(Double.valueOf(Utils.formatAmount(String.valueOf((parentCalculatedValue - userCalculatedValue)))));
                // call the same method again with new users
                System.out.println("User fee value is " + userFee.getCalculatedValue());
                System.out.println("Old Parent fee value is " + parentCalculatedValue);
                System.out.println("New Parent fee value is " + parentUserFee.getCalculatedValue());
                calculateFee(user.getChild(), user, userFee);
            }
        }
    }
}
