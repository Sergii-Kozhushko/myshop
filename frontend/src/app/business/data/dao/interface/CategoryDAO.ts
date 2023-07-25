import {CommonDAO} from './CommonDAO';
import {Category} from '../../../../model/Models';

export interface CategoryDAO extends CommonDAO<Category> {

  // поиск категорий по любым параметрам, указанных в CategorySearchValues
  // findCategories(categorySearchValues: CategorySearchValues): Observable<Category[]>;

}
