package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.ItemSale;
import git.matheusoliveira04.api.store.models.Sale;
import git.matheusoliveira04.api.store.repositories.SaleRepository;
import git.matheusoliveira04.api.store.services.SaleService;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public List<Sale> findAll(Pageable pageable) {
        List<Sale> saleList = saleRepository.findAll(pageable).toList();
        if (saleList.isEmpty()) {
            throw new ObjectNotFoundException("No sale found.");
        }
        return saleList;
    }

    @Override
    public Sale findById(UUID id) {
        return saleRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Sale not found with id: " + id));
    }

    @Override
    public Sale insert(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public Sale update(Sale sale) {
        findById(sale.getId());
        return saleRepository.save(sale);
    }

    @Override
    public void delete(UUID id) {
        saleRepository.delete(findById(id));
    }

    @Override
    public Sale addTotalValue(Sale sale, BigDecimal addValue, Integer quantityItemSale) {
        sale.setValueTotal(sale.getValueTotal().add(addValue.multiply(BigDecimal.valueOf(quantityItemSale))));
        return sale;
    }

    @Override
    public Sale subtractTotalValue(Sale sale, BigDecimal subtractValue, Integer quantityItemSale) {
        sale.setValueTotal(sale.getValueTotal().subtract(subtractValue.multiply(BigDecimal.valueOf(quantityItemSale))));
        return sale;
    }

    @Override
    public Sale addTotalQuantity(Sale sale, Integer addQuantity) {
        sale.setQuantityTotal(sale.getQuantityTotal() + addQuantity);
        return sale;
    }

    @Override
    public Sale subtractTotalQuantity(Sale sale, Integer subtractQuantity) {
        sale.setQuantityTotal(sale.getQuantityTotal() - subtractQuantity);
        return sale;
    }
}
