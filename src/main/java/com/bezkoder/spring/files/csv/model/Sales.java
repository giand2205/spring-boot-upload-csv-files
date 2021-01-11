package com.bezkoder.spring.files.csv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "sales_records")
@AllArgsConstructor
public class Sales implements Persistable<Integer> {

  @Column
  private String region;

  @Column
  private String country;

  @Column(name = "item_type")
  private String itemType;

  @Column(name = "sales_channel")
  private String salesChannel;

  @Column(name = "order_priority")
  private String orderPriority;

  @Column(name = "order_date")
  private Date orderDate;

  @Id
  @Column(name = "order_id")
  private Integer orderId;

  @Column(name = "ship_date")
  private Date shipDate;

  @Column(name = "units_sold")
  private Integer unitsSold;

  @Column(name = "unit_price")
  private Double unitPrice;

  @Column(name = "unit_cost")
  private Double unitCost;

  @Column(name = "total_revenue")
  private Double totalRevenue;

  @Column(name = "total_cost")
  private Double totalCost;

  @Column(name = "total_profit")
  private Double totalProfit;


  public Sales() {

  }

  @Override
  public Integer getId() {
    return orderId;
  }

  @Override
  public boolean isNew() {
    return true;
  }

}
