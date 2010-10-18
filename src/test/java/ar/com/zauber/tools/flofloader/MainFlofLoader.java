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

import java.io.File;
import java.io.IOException;

import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFParseException;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.labs.kraken.vocabularies.flof.FlofPlace;

/**
 * Example of how to load flof rdf dump using java.
 * 
 * @author Juan F. Codagnone
 * @since Oct 17, 2010
 */
public final class MainFlofLoader {

    /** constructor */
    private MainFlofLoader() {
        // void
    }
    
    /** main */ 
    public static void main(final String[] args) throws RepositoryException, 
                                                RDFParseException, IOException {
        new FlofDumpClosureProcessor(new File("flof.ttl")).process(
                new Closure<FlofPlace>() {
            @Override
            public void execute(final FlofPlace place) {
                System.out.println("----------------------");
                System.out.println(place);
                System.out.println(place.getTitle());
                System.out.println(place.getGeoLocation());
            }
        });
    }
}
