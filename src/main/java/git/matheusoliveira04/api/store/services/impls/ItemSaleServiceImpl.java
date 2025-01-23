package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.ItemSale;
import git.matheusoliveira04.api.store.models.Product;
import git.matheusoliveira04.api.store.models.Sale;
import git.matheusoliveira04.api.store.repositories.ItemSaleRepository;
import git.matheusoliveira04.api.store.services.ItemSaleService;
import git.matheusoliveira04.api.store.services.ProductService;
import git.matheusoliveira04.api.store.services.SaleService;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ItemSaleServiceImpl implements ItemSaleService {

    @Autowired
    private ItemSaleRepository itemSaleRepository;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Override
    public List<ItemSale> findAll(Pageable pageable) {
        List<ItemSale> itemSaleList = itemSaleRepository.findAll(pageable).toList();
        if (itemSaleList.isEmpty()) {
            throw new ObjectNotFoundException("No Item sale found.");
        }
        return itemSaleList;
    }

    @Override
    public ItemSale findById(UUID id) {
        return itemSaleRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Item sale not found with id: " + id));
    }

    @Override
    @Transactional
    public ItemSale insert(ItemSale itemSale) {
        subtractProductStock(itemSale);
        addSaleTotalQuantity(itemSale);
        addSaleTotalValue(itemSale);

        updateProduct(itemSale.getProduct());
        updateSale(itemSale.getSale());
        return itemSaleRepository.save(itemSale);
    }

    @Override
    @Transactional
    public ItemSale update(ItemSale itemSale) {
        ItemSale itemSaleFound = findById(itemSale.getId());
        updateProductStock(itemSale, itemSaleFound);
        updateSaleTotalQuantity(itemSale, itemSaleFound);
        updateSaleTotalValue(itemSale, itemSaleFound);

        updateProduct(itemSale.getProduct());
        updateSale(itemSale.getSale());
        return itemSaleRepository.save(itemSale);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ItemSale itemSale = findById(id);
        addProductStock(itemSale);
        subtractSaleTotalQuantity(itemSale);
        subtractSaleTotalValue(itemSale);

        updateProduct(itemSale.getProduct());
        updateSale(itemSale.getSale());
        itemSaleRepository.delete(itemSale);
    }

    private void updateProduct(Product product) {
        productService.update(product);
    }

    private void updateSale(Sale sale) {
        saleService.update(sale);
    }

    private void addSaleTotalValue(ItemSale itemSale) {
        saleService.addTotalValue(itemSale.getSale(), itemSale.getValue(), itemSale.getQuantity());
    }

    private void subtractSaleTotalValue(ItemSale itemSale) {
        saleService.subtractTotalValue(itemSale.getSale(), itemSale.getValue(), itemSale.getQuantity());
    }

    private void addSaleTotalQuantity(ItemSale itemSale) {
        saleService.addTotalQuantity(itemSale.getSale(), itemSale.getQuantity());
    }

    private void subtractSaleTotalQuantity(ItemSale itemSale) {
        saleService.subtractTotalQuantity(itemSale.getSale(), itemSale.getQuantity());
    }

    private void updateSaleTotalValue(ItemSale itemSale, ItemSale itemSaleFound) {
        saleService.addTotalValue(itemSale.getSale(), itemSale.getValue(), itemSale.getQuantity());
        saleService.subtractTotalValue(itemSale.getSale(), itemSaleFound.getValue(), itemSaleFound.getQuantity());
    }

    private void updateSaleTotalQuantity(ItemSale itemSale, ItemSale itemSaleFound) {
        saleService.addTotalQuantity(itemSale.getSale(), itemSale.getQuantity());
        saleService.subtractTotalQuantity(itemSale.getSale(), itemSaleFound.getQuantity());
    }

    private void subtractProductStock(ItemSale itemSale) {
        productService.subtractStock(itemSale.getProduct(), itemSale.getQuantity());
    }

    private void addProductStock(ItemSale itemSale) {
        productService.addStock(itemSale.getProduct(), itemSale.getQuantity());
    }

    private void updateProductStock(ItemSale itemSale, ItemSale itemSaleFound) {
        addProductStock(itemSaleFound);
        subtractProductStock(itemSale);
    }
}

