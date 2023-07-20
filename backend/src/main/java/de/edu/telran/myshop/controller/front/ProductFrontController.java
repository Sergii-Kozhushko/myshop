/**
 * ProductFrontController.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 09-Jul-2023 18:04
 */

package de.edu.telran.myshop.controller.front;

import de.edu.telran.myshop.service.impl.ProductCategoryServiceImpl;
import de.edu.telran.myshop.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(ProductFrontController.MAIN_PATH)
@RequiredArgsConstructor
public class ProductFrontController {
   private final ProductServiceImpl productService;
   private final ProductCategoryServiceImpl productCategoryService;
   public static final String MAIN_PATH = "/front/product";

   @GetMapping("/all")
   public String getAllProducts(Model model,
                                @RequestParam(required = false, name="cid") Integer category_id) {
      model.addAttribute("products", productService.getProductsByCategory(category_id));
      model.addAttribute("categories", productCategoryService.getAllCategories());
      model.addAttribute("current_category", productCategoryService.getById(category_id));
      return "products";
   }




}
