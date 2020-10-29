package com.arademia.aqar.repos;

import com.arademia.aqar.entity.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice,Integer> {
}
