package br.com.santacruz.portal.modelo;

import br.com.santacruz.portal.modelo.dto.LogDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String process;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String userLogin;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "log")
    @JsonIgnore
    private List<LogDetail> logDetails;

    public Log(String process, LocalDate startDate, LocalDate endDate, String status, String userLogin){
        this.process = process;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.userLogin = userLogin;
    }

}
