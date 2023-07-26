import {CommonDAO} from './CommonDAO';
import {Category, Product} from '../../../../model/Models';

export interface ProductDAO extends CommonDAO<Product> {

  // поиск категорий по любым параметрам, указанных в CategorySearchValues
  // findCategories(categorySearchValues: CategorySearchValues): Observable<Category[]>;

}
