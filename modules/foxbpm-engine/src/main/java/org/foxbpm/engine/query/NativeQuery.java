package org.foxbpm.engine.query;

import java.util.List;

public interface NativeQuery<T extends NativeQuery< ? , ? >, U extends Object> {

  /**
   * Hand in the SQL statement you want to execute. BEWARE: if you need a count you have to hand in a count() statement
   * yourself, otherwise the result will be treated as lost of Activiti entities.
   * 
   * If you need paging you have to insert the pagination code yourself. We skipped doing this for you
   * as this is done really different on some databases (especially MS-SQL / DB2)
   */
  T sql(String selectClause);
  
  /**
   * Add parameter to be replaced in query for index, e.g. :param1, :myParam, ...
   */
  T parameter(String name, Object value);

  /** Executes the query and returns the number of results */
  long count();

  /**
   * Executes the query and returns the resulting entity or null if no
   * entity matches the query criteria.
   * @throws ActivitiException when the query results in more than one
   * entities.
   */
  U singleResult();

  /** Executes the query and get a list of entities as the result. */
  List<U> list();
  
  /** Executes the query and get a list of entities as the result. */
  List<U> listPage(int firstResult, int maxResults);
}
