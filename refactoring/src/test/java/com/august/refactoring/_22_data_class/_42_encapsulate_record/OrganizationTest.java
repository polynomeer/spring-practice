package com.august.refactoring._22_data_class._42_encapsulate_record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrganizationTest {

    @Test
    void name() {
        Organization organization = new Organization();
        organization.name = "august"; // 직접적인 접근이 가능
    }
}