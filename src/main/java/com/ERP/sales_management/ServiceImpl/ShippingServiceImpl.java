package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.Model.Shipping;
import com.ERP.sales_management.Repository.ShippingRepository;
import com.ERP.sales_management.Service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

    @Autowired
    public ShippingServiceImpl(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @Override
    public Shipping createShipping(Shipping shipping) {
        return shippingRepository.save(shipping);
    }

    @Override
    public List<Shipping> getAllShippingRecords() {
        return shippingRepository.findAll();
    }

    @Override
    public Shipping getShippingById(Long id) {
        return shippingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Shipping> getShippingByOrderId(Long orderId) {
        return shippingRepository.findByOrderId(orderId);
    }

    @Override
    public Shipping updateShipping(Long id, Shipping shipping) {
        Optional<Shipping> existing = shippingRepository.findById(id);
        if (existing.isPresent()) {
            shipping.setId(id);
            return shippingRepository.save(shipping);
        }
        return null;
    }

    @Override
    public void deleteShipping(Long id) {
        shippingRepository.deleteById(id);
    }
}
