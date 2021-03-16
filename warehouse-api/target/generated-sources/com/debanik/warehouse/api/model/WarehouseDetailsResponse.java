package com.debanik.warehouse.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * WarehouseDetailsResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-03-16T02:09:42.318+05:30")

public class WarehouseDetailsResponse   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("ProductName")
  private String productName = null;

  @JsonProperty("WarehouseCapacity")
  private String warehouseCapacity = null;

  public WarehouseDetailsResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * warehouse uuid
   * @return id
  **/
  @ApiModelProperty(required = true, value = "warehouse uuid")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public WarehouseDetailsResponse productName(String productName) {
    this.productName = productName;
    return this;
  }

  /**
   * name of the product
   * @return productName
  **/
  @ApiModelProperty(required = true, value = "name of the product")
  @NotNull


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public WarehouseDetailsResponse warehouseCapacity(String warehouseCapacity) {
    this.warehouseCapacity = warehouseCapacity;
    return this;
  }

  /**
   * Warehouse capacity
   * @return warehouseCapacity
  **/
  @ApiModelProperty(required = true, value = "Warehouse capacity")
  @NotNull


  public String getWarehouseCapacity() {
    return warehouseCapacity;
  }

  public void setWarehouseCapacity(String warehouseCapacity) {
    this.warehouseCapacity = warehouseCapacity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WarehouseDetailsResponse warehouseDetailsResponse = (WarehouseDetailsResponse) o;
    return Objects.equals(this.id, warehouseDetailsResponse.id) &&
        Objects.equals(this.productName, warehouseDetailsResponse.productName) &&
        Objects.equals(this.warehouseCapacity, warehouseDetailsResponse.warehouseCapacity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, productName, warehouseCapacity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WarehouseDetailsResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
    sb.append("    warehouseCapacity: ").append(toIndentedString(warehouseCapacity)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

