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
 * WarehouseEditRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-03-16T02:09:42.318+05:30")

public class WarehouseEditRequest   {
  @JsonProperty("capacity")
  private String capacity = null;

  public WarehouseEditRequest capacity(String capacity) {
    this.capacity = capacity;
    return this;
  }

  /**
   * updated content of the warehouse entry
   * @return capacity
  **/
  @ApiModelProperty(value = "updated content of the warehouse entry")


  public String getCapacity() {
    return capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WarehouseEditRequest warehouseEditRequest = (WarehouseEditRequest) o;
    return Objects.equals(this.capacity, warehouseEditRequest.capacity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(capacity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WarehouseEditRequest {\n");
    
    sb.append("    capacity: ").append(toIndentedString(capacity)).append("\n");
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

