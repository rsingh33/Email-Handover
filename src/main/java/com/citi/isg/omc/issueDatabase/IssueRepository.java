package com.citi.isg.omc.issueDatabase;

        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByIssueDescriptionStartsWithIgnoreCase(String issueDescription);


}