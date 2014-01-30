package com.recruiters.web.form;

import com.recruiters.model.Employer;
import com.recruiters.model.User;
import com.recruiters.model.Vacancy;
import com.recruiters.model.status.VacancyStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Vacancy Form
 */
public class VacancyForm {
    private Long id = 0L;
    private String title = null;
    private String description = null;
    private Long salaryFrom = 0L;
    private Long salaryTo = 0L;
    private Date expirationDate = null;
    private MultipartFile testFile = null;
    private Boolean publish = false;

    public VacancyForm() {
    }

    public Vacancy fillModel(final User user) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(id);
        vacancy.setTitle(title);
        vacancy.setDescription(description);
        vacancy.setSalaryFrom(salaryFrom);
        vacancy.setSalaryTo(salaryTo);
        vacancy.setCreationDate(new Date());
        vacancy.setExpirationDate(expirationDate);
        vacancy.setEmployer(new Employer(user.getEmployerId(), user));
        if (publish) {
            vacancy.setStatus(VacancyStatus.ACTIVE);
        } else {
            vacancy.setStatus(VacancyStatus.UNPUBLISHED);
        }
        return vacancy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(final Long salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Long getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(final Long salaryTo) {
        this.salaryTo = salaryTo;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public MultipartFile getTestFile() {
        return testFile;
    }

    public void setTestFile(final MultipartFile testFile) {
        this.testFile = testFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(final Boolean publish) {
        this.publish = publish;
    }
}
