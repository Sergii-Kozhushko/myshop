import {CommonDAO} from './CommonDAO';
import {Customer} from '../../../../model/Models';

export interface CustomerDAO extends CommonDAO<Customer> {

  // поиск категорий по любым параметрам, указанных в CategorySearchValues
  // findCategories(categorySearchValues: CategorySearchValues): Observable<Category[]>;

}
