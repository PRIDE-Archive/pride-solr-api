package uk.ac.ebi.pride.solr.indexes.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.solr.client.solrj.SolrServerException;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.pride.solr.commons.PrideSolrProject;
import uk.ac.ebi.pride.solr.commons.dto.FindByKeywordInputDto;
import uk.ac.ebi.pride.solr.indexes.services.SolrProjectService;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("project")
@Tag(name = "Project")
public class ProjectController {

    private SolrProjectService solrProjectService;

    public ProjectController(SolrProjectService SolrProjectService) {
        this.solrProjectService = SolrProjectService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrideSolrProject> save(@RequestBody PrideSolrProject prideSolrProject) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solrProjectService.save(prideSolrProject));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrideSolrProject> update(@RequestBody PrideSolrProject prideSolrProject) {
        return ResponseEntity.status(HttpStatus.OK).body(solrProjectService.update(prideSolrProject));
    }

    @RequestMapping(value = "/upsert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrideSolrProject> upsert(@RequestBody PrideSolrProject prideSolrProject) {
        return ResponseEntity.status(HttpStatus.OK).body(solrProjectService.upsert(prideSolrProject));
    }

    @RequestMapping(value = "/deleteProjectById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteProjectById(@RequestParam String id) {
        solrProjectService.deleteProjectById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAll() {
        solrProjectService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveAll(List<PrideSolrProject> projects) {
        solrProjectService.saveAll(projects);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
    @RequestMapping(value = "/findByAccession", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrideSolrProject> findByAccession(@RequestParam("accession") String accession) {
        return ResponseEntity.ok(solrProjectService.findByAccession(accession));
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PrideSolrProject>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(SolrProjectService.findAll());
    }


    @RequestMapping(value = "/findAllUsingCursor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterator<PrideSolrProject>> findAllUsingCursor() {
        return ResponseEntity.ok(SolrProjectService.findAllUsingCursor());
    }

    @RequestMapping(value = "/findByAccession", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrideSolrProject> findByAccession(@RequestParam("accession") String accession) {
        return ResponseEntity.ok(SolrProjectService.findByAccession(accession));
    }

    @RequestMapping(value = "/findByKeyword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public ResponseEntity<HighlightPage<PrideSolrProject>> findByKeyword(
            @RequestBody FindByKeywordInputDto findByKeywordInputDto, @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(SolrProjectService.findByKeyword(findByKeywordInputDto.getKeywords(), findByKeywordInputDto.getFilterQuery(),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()),
                findByKeywordInputDto.getDateGap()));
    }

    @RequestMapping(value = "/findAllIgnoreCase", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public ResponseEntity<Page<PrideSolrProject>> findAllIgnoreCase(@Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(SolrProjectService.findAllIgnoreCase(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort())));

    }

    @RequestMapping(value = "/findAllFacetIgnoreCase", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public ResponseEntity<FacetPage<PrideSolrProject>> findAllFacetIgnoreCase(@Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(SolrProjectService.findAllFacetIgnoreCase(pageable));
    }

    @RequestMapping(value = "/findFacetByKeyword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FacetPage<PrideSolrProject>> findFacetByKeyword(@RequestBody FindByKeywordInputDto findByKeywordInputDto,
                                                                          @Parameter(hidden = true) Pageable pageable) {
        FacetPage<PrideSolrProject> facetPage = SolrProjectService.findFacetByKeyword(findByKeywordInputDto.getKeywords(),
                findByKeywordInputDto.getFilterQuery(), PageRequest.of(0, 10),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), findByKeywordInputDto.getDateGap());
        return ResponseEntity.ok(facetPage);
    }

    @RequestMapping(value = "/findSimilarProjects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public ResponseEntity<List<PrideSolrProject>> findSimilarProjects(String accession, @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(SolrProjectService.findSimilarProjects(accession, pageable.getPageSize(), pageable.getPageNumber()));
    }

    @RequestMapping(value = "/findAutoComplete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> findAutoComplete(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(SolrProjectService.findAutoComplete(keyword));
    }

    @RequestMapping(value = "/findProjectAccessionsWithEmptyPeptideSequencesOrProteinIdentifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> findProjectAccessionsWithEmptyPeptideSequencesOrProteinIdentifications() throws IOException, SolrServerException {
        return ResponseEntity.ok(SolrProjectService.findProjectAccessionsWithEmptyPeptideSequencesOrProteinIdentifications());
    }

    @RequestMapping(value = "/findProjectAccessionsWithEmptyFileNames", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> findProjectAccessionsWithEmptyFileNames() throws IOException, SolrServerException {
        return ResponseEntity.ok(SolrProjectService.findProjectAccessionsWithEmptyFileNames());
    }*/

}
