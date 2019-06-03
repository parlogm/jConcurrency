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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_ID_SEQ")
    @SequenceGenerator(name = "CLIENT_ID_SEQ", sequenceName = "CLIENT_ID_SEQ")
    private Long id;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isCorporate;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @ManyToOne
    @JoinColumn(name = "priceNomId")
    private PriceNomenclature priceGroup;
    private Long salesAgentId;
    @ManyToOne
    @JoinColumn(name = "fidelityNomId")
    private FidelityNomenclature fidelityNomenclature;
    private Date lastBillingDate;
    private Float billingRateIndex;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean emailConfirmation;
    private Integer daysFromLastBill;
    private String email;
    @ManyToOne
    @JoinColumn(name = "centerNomId")
    private CenterNomenclature assignedCenter;
    @ManyToOne
    @JoinColumn(name = "countryId")
    private CountryNomenclature countryNomenclature;
    private String address;
    private String contactPhone;
    private String contact;
    @ManyToOne
    @JoinColumn(name = "paymentNomId")
    private PaymentNomenclature paymentMethod;
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
    private String finOutcome;
    @ManyToOne
    @JoinColumn(name = "orgNomId")
    private OrgNomenclature orgType;
    private Float salesAmount;
    private String comment;

}
