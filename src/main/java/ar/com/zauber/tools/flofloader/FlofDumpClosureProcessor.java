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

import org.apache.commons.lang.Validate;
import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.model.Resource;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.nativerdf.NativeStore;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.ClosureProcessor;
import ar.com.zauber.labs.kraken.vocabularies.flof.FlofPlace;

/**
 * Loads Flof RDF dump.
 * 
 * @author Juan F. Codagnone
 * @since Oct 18, 2010
 */
public class FlofDumpClosureProcessor implements ClosureProcessor<FlofPlace> {
    private final ClosureProcessor<FlofPlace> target;
    
    /** Creates the FlofDumpClosureProcessor. */
    public FlofDumpClosureProcessor(final File dump) 
      throws RepositoryException, RDFParseException, IOException {
        Validate.notNull(dump, "file is null!");
        
        final Repository repo = new SailRepository(new ForwardChainingRDFSInferencer(
                new NativeStore(new File("flof-store"))));
        repo.initialize();
        final RepositoryConnection con = repo.getConnection();
        try {
            con.clear();
            con.add(dump, 
                    "http://flof/anonymous", 
                    RDFFormat.forFileName(dump.getAbsolutePath()), 
                    new Resource[]{});
        } finally {
            con.close();
        }
        target = new ElmoEntityClosureProcessor<FlofPlace>(new SesameManagerFactory(
                new ElmoModule(), repo), FlofPlace.class);
        
    }
    
    @Override
    public final void process(final Closure<FlofPlace> closure) {
        target.process(closure);
    }
}
