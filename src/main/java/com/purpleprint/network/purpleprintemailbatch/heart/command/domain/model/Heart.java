package com.purpleprint.network.purpleprintemailbatch.heart.command.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * <pre>
 * Class : Heart
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-21       전현정           최초 생성
 * </pre>
 *
 * @author 전현정(최초 작성자)
 * @version 1(클래스 버전)
 * @see
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tbl_heart")
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Integer id;

    @Column(name = "giver", nullable = false)
    private int giver;

    @Column(name = "recipient", nullable = false)
    private int recipient;

    @Column(name = "gave_at", columnDefinition = "timestamp default now()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gaveAt;

}

