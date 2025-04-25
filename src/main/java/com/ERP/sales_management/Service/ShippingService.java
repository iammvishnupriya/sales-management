package com.ERP.sales_management.Service;



import com.ERP.sales_management.Model.Shipping;

import java.util.List;

public interface ShippingService {

    Shipping createShipping(Shipping shipping);

    List<Shipping> getAllShippingRecords();

    Shipping getShippingById(Long id);

    List<Shipping> getShippingByOrderId(Long orderId);

    Shipping updateShipping(Long id, Shipping shipping);

    void deleteShipping(Long id);
}
