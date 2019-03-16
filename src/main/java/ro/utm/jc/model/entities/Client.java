package ro.utm.jc.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isCorporate;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String clientPriceGroup;
    private Long salesAgentId;
    private Long fidelityCardId;
    private String fidelityGroup;
    private Date lastBillingDate;
    private Float billingRateIndex;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean emailConfirmation;
    private Integer daysFromLastBill;
    private String email;
    private String assignedCenter;
    private String country;
    private String countryCode;
    private String address;
    private String contactPhone;
    private String contact;
    private String paymentMethod;
    private Integer paymentDueIn;
    private Integer paymentNotification;
    private Long contractNr;
    private Date contractExpirationDate;
    private String regCode;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isVatApplicable;
    private Float balance;
    private Float debit;
    private Float credit;
    private Float creditApprovedLimit;
    private Float creditLimit;
    private Timestamp legalNoticeReceivedOn;
    private Timestamp legalNoticeSentOn;
    private String legalNoticeOutcome;
    private Timestamp cipReceivedOn;
    private Timestamp cipSentOn;
    private String cipOutcome;
    private Timestamp finNoticeReceivedOn;
    private Timestamp finNoticeSentOn;
    private String finNoticeOutcome;
    private String orgType;
    private Float salesAmount;
    private String comment;

}
