/*
 *  R2R
 *
 *  Copyright 2010-2011 Freie Universität Berlin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package controllers;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import de.fuberlin.wiwiss.r2r.ExportableSource;

import java.io.*;
import java.nio.charset.Charset;

import play.api.mvc.AnyContent;
import play.api.mvc.Request;

public class StreamSource implements ExportableSource {

    private Model model;

    public StreamSource(final Request<AnyContent> request) {
        this(request.body().asText().get());

    }

    public StreamSource(final String stream) {
        model = ModelFactory.createDefaultModel();
        final Reader reader = new StringReader(stream);
        model.read(
                reader,
                "http://example.com/", "TURTLE");
    }

    public QueryExecution executeQuery(String query) {
        return QueryExecutionFactory.create(query, model);
    }

    public Model executeDescribeQuery(String query) {
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        Model model = qe.execDescribe();
        qe.close();
        return model;
    }

    public Model executeConstructQuery(String query) {
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        Model model = qe.execConstruct();
        qe.close();
        return model;
    }

    public void exportMappings(Writer out, String serializationFormat) {
        model.write(out, serializationFormat);
    }

    public void exportMappings(OutputStream out, String serializationFormat) {
        model.write(out, serializationFormat);
    }

    public void exportMappings(Writer out) {
        model.write(out, "N-TRIPLE");
    }

    public void exportMappings(OutputStream out) {
        model.write(out, "N-TRIPLE");
    }
}
