package tomskasu.sancor.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "worker")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String fatherName;
    private String mail;
    private String birth;
    private String position;
    private int height;
    private int weight;

    private float IMT;

    private String IMT_COLOR;

    private int CHSS;

    private String CHSS_COLOR;

    private int SAD;

    private String SAD_COLOR;

    private int DAD;

    private String DAD_COLOR;

    private int SATURATION;

    public float getTEMP() {
        return TEMP;
    }

    public void setTEMP(float TEMP) {
        this.TEMP = TEMP;
    }

    private float TEMP;

    private String TEMP_COLOR;

    private String SATURATION_COLOR;
    private int age;
    private String sex;



}
