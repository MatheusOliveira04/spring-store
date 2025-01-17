package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Price;
import git.matheusoliveira04.api.store.repositories.PriceRepository;
import git.matheusoliveira04.api.store.services.PriceService;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public void validateSalePriceIsGreaterThanCostPrice(Price price) {
        if (price.getSalePrice().compareTo(price.getCostPrice()) < 0) {
            throw new IntegrityViolationException("The selling price must be greater than or equal to the cost price.");
        }
    }

    @Override
    public List<Price> findAll(Pageable pageable) {
        List<Price> priceList = priceRepository.findAll(pageable).toList();
        if (priceList.isEmpty()) {
            throw new ObjectNotFoundException("No price found!");
        }
        return priceList;
    }

    @Override
    public Price findById(UUID id) {
        return priceRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Price not found with id: " + id));
    }

    @Override
    public Price insert(Price price) {
        validateSalePriceIsGreaterThanCostPrice(price);
        return priceRepository.save(price);
    }

    @Override
    public Price update(Price price) {
        validateSalePriceIsGreaterThanCostPrice(price);
        findById(price.getId());
        return priceRepository.save(price);
    }

    @Override
    public void delete(UUID id) {
        priceRepository.delete(findById(id));
    }
}
