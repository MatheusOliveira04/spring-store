package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Product;
import git.matheusoliveira04.api.store.repositories.ProductRepository;
import git.matheusoliveira04.api.store.services.ProductService;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private void validateUniqueCodeBar(Product product) {
        Product find = productRepository.findByCodeBar(product.getCodeBar()).get();
        if (find != null && find.getId() != product.getId()) {
            throw new IntegrityViolationException("A code bar already exists for another product. Each product must have a unique code bar.");
        }
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

    @Override
    public Product insert(Product product) {
        validateUniqueCodeBar(product);
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        findById(product.getId());
        validateUniqueCodeBar(product);
        return productRepository.save(product);
    }

    @Override
    public void delete(UUID id) {
        productRepository.delete(findById(id));
    }
}
