package br.com.santacruz.portal.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_logs_details")
public class LogDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String information;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy", iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOccured;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "log_id")
    private Log log;

    public LogDetail(String description, String information, LocalDate dateOccured, String status){
        this.description = description;
        this.information = information;
        this.dateOccured = dateOccured;
        this.status = status;
    }

}
