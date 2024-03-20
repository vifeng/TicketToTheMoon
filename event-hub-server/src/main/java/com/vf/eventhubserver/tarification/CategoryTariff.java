package com.vf.eventhubserver.tarification;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class CategoryTariff implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 50)
  private String name;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      optional = false)
  @JoinColumn(name = "Tarification_FK")
  private Tarification tarification;

  public CategoryTariff() {}

  public CategoryTariff(Long id, String name, Tarification tarification) {
    setId(id);
    setName(name);
    setTarification(tarification);
  }

  public CategoryTariff(String name, Tarification tarification) {
    setName(name);
    setTarification(tarification);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Tarification getTarification() {
    return tarification;
  }

  public void setTarification(Tarification tarification) {
    this.tarification = tarification;
  }

  @Override
  public String toString() {
    return "CategoryTariff [id=" + id + ", name=" + name + ", tarification=" + tarification + "]";
  }
}
