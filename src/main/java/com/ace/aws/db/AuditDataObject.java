package com.ace.aws.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "audit")
@NamedQuery(name = "listAllAudits", query = "select ado from AuditDataObject ado")
public class AuditDataObject
{
    @Column(name = "row_id")
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @SuppressWarnings({"UnusedDeclaration"})
    private String id;

    @Column(name = "entity_type")
    @SuppressWarnings("FieldCanBeLocal,UnusedDeclaration")
    private String entityType;

    @Column(name = "entity_id")
    @SuppressWarnings({"FieldCanBeLocal,UnusedDeclaration"})
    private String entityId;

    @Column(name = "event")
    @SuppressWarnings({"FieldCanBeLocal,UnusedDeclaration"})
    private String event;

    @Column(name = "host_name")
    @SuppressWarnings({"FieldCanBeLocal,UnusedDeclaration"})
    private String hostName;

    @CreationTimestamp
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    @SuppressWarnings({"UnusedDeclaration"})
    private ZonedDateTime createDate;

    @SuppressWarnings({"unused"})
    public AuditDataObject()
    {
    }

    public AuditDataObject(String entityType, String entityId, String event, String hostName)
    {
        this.entityType = entityType;
        this.entityId = entityId;
        this.event = event;
        this.hostName = hostName;
    }

    public String getId()
    {
        return id;
    }

    public String getEntityType()
    {
        return entityType;
    }

    public String getEntityId()
    {
        return entityId;
    }

    public ZonedDateTime getCreateDate()
    {
        return createDate;
    }

    public String getEvent()
    {
        return event;
    }

    public String getHostName()
    {
        return hostName;
    }
}
