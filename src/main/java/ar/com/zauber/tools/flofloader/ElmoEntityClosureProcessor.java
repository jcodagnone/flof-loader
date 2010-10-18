/**
 * Copyright (c) 2009-2010 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.tools.flofloader;

import org.apache.commons.lang.Validate;
import org.openrdf.elmo.sesame.SesameManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.ClosureProcessor;
import ar.com.zauber.commons.dao.closure.processors.IterableClosureProcesor;

/**
 * {@link ClosureProcessor} que busca todas las entidades de cierto tipo
 * en un repositorio y lo publica en un closure.
 *
 * @author Mariano Semelman
 * @since Dec 2, 2009
 * @param <T>
 */
public class ElmoEntityClosureProcessor<T>
   implements ClosureProcessor<T> {
    private final SesameManagerFactory managerFactory;
    private final Class<T> clazz;

    /** Creates the LocationClosureProcessor. *
     */
    public ElmoEntityClosureProcessor(final SesameManagerFactory managerFactory,
            final Class<T> clazz) {
        Validate.notNull(managerFactory);
        Validate.notNull(clazz);

        this.managerFactory = managerFactory;
        this.clazz = clazz;
    }

    /** @see ClosureProcessor#process(Closure) */
    public final void process(final Closure<T> closure) {
        final SesameManager manager = managerFactory.createElmoManager();
        try {
            new IterableClosureProcesor<T>(manager.findAll(
                    clazz)).process(closure);
        } finally {
            manager.close();
        }
    }
}
