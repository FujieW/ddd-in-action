package com.example.dddinaction.domain.organization.emp;

import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.common.framework.domain.AuditableEntity;
import com.example.dddinaction.domain.organization.post.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 员工
 */

@Getter
public class Employee extends AuditableEntity {
    @Setter
    private Long id;
    private final Long tenantId;
    @Setter
    private Long orgId;
    @Setter
    private String name;
    @Setter
    private String num;
    @Setter
    private String idNum;
    @Setter
    private LocalDateTime dob;
    private EmpStatus status;
    private final List<Skill> skills;
    private final List<Post> posts;

    public Employee(Long tenantId, LocalDateTime createdAt, Long createdBy) {
        super();
        this.tenantId = tenantId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        status = EmpStatus.REGULAR;
        skills = new ArrayList<>();
        posts = new ArrayList<>();
    }

    public void becomeRegular() {
        status = this.status.becomeRegular();
    }

    public void terminate() {
        status = this.status.terminate();
    }

    public Optional<Skill> getSkill(Long skillTypeId) {
        return skills.stream().filter(skill -> skill.getSkillTypeId().equals(skillTypeId)).findAny();
    }

    public List<Skill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    public void addSkill(Long tenantId, Long skillTypeId, SkillLevel skillLevel, int duration, Long userId) {
        skillTypeShouldNotDuplicated(skillTypeId);
        Skill skill = new Skill(tenantId, skillTypeId, LocalDateTime.now(), userId);
        skill.setSkillLevel(skillLevel);
        skill.setDuration(duration);
        skills.add(skill);
    }

    private void skillTypeShouldNotDuplicated(Long skillTypeId) {
        if (getSkill(skillTypeId).isPresent()) {
            throw new IllegalArgumentException("该员工已拥有该技能");
        }
    }

    public Employee updateSKill(Long skillId, SkillLevel skillLevel, int duration, Long userId) {
        Skill skill = this.getSkill(skillId).orElseThrow(() -> new BusinessException("该员工没有该技能"));
        if (skill.getSkillLevel()!= skillLevel || skill.getDuration()!= duration) {
            skill.setSkillLevel(skillLevel);
            skill.setDuration(duration);
            skill.setLastUpdatedAt(LocalDateTime.now());
            skill.setLastUpdatedBy(userId);
            skill.toUpdate();
        }
        return this;
    }

    public Employee deleteSkill(Long skillId) {
        this.getSkill(skillId).orElseThrow(() -> new BusinessException("该员工没有该技能")).toDelete();
        return this;
    }


}
