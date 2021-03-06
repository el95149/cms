/*
 * Copyright(c) 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.sastix.cms.server.domain.repositories;

import com.sastix.cms.server.domain.entities.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ResourceRepository extends CrudRepository<Resource, Integer> {

    List<Resource> findByName(@Param("name") String name);

    @Query("SELECT r FROM Resource r WHERE r.uid = :uid")
    List<Resource> findByUID(@Param("uid") String uid);
    
    @Modifying
    @Query("update Resource res set res.author = :author where res.id = :id")
    Integer updateAuthor(@Param("id") int id, @Param("author") String author);


    Resource findOneByUid(@Param("uid") String uid);

    List<Resource> findByUidOrderByIdDesc(@Param("uid") String uid, Pageable pageable);
    List<Resource> findByUidOrderByIdAsc(@Param("uid") String uid, Pageable pageable);
    
    @Query("select r1.resource from Revision r1 where r1.title = "
    		+ "(select max(r2.title) from Revision r2 where r1.resource = r2.resource)"
    		+ " and r1.deletedAt is null order by r1.resource.id")
    List<Resource> findCurrent();

}
