package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Product;
import git.matheusoliveira04.api.store.repositories.ProductRepository;
import git.matheusoliveira04.api.store.services.PriceService;
import git.matheusoliveira04.api.store.services.ProductService;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private void validateUniqueCodeBar(Product product) {
        Product find = productRepository.findByCodeBar(product.getCodeBar());
        if (find != null && find.getId() != product.getId()) {
            throw new IntegrityViolationException("A code bar already exists for another product. Each product must have a unique code bar.");
        }
    }

    private void validateUniquePrice(Product product) {
        Product find = productRepository.findByPrice(product.getPrice());
        if(find != null && find.getId() != product.getId()) {
            throw new IntegrityViolationException("A price already exists for another product. Each product must have a unique price.");
        }
    }

    private void validateProduct(Product product) {
        validateUniqueCodeBar(product);
        validateUniquePrice(product);
    }

    @Override
    public List<Product> findAll(Pageable pageable) {
        List<Product> productList = productRepository.findAll(pageable).toList();
        if (productList.isEmpty()) {
            throw new ObjectNotFoundException("No client found!");
        }
        return productList;
    }

    @Override
    public Product findById(UUID id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Client not found with id: " + id));
    }

    @Transactional
    @Override
    public Product insert(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Product update(Product product) {
        Product productFound = findById(product.getId());
        validateProduct(product);
        return productRepository.save(product);
    }

    @Override
    public void delete(UUID id) {
        productRepository.delete(findById(id));
    }
}
