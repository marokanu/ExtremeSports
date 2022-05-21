package com.sport.admin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "locations")
@NoArgsConstructor
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 256, nullable = false)
    private String name;

    @Column(length = 4096, nullable = false)
    private String Description;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateIn;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateOut;

    private float price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

}
