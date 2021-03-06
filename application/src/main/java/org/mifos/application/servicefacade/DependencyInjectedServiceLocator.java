/*
 * Copyright (c) 2005-2010 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */
package org.mifos.application.servicefacade;

import org.mifos.accounts.loan.persistance.ClientAttendanceDao;
import org.mifos.accounts.loan.persistance.LoanPersistence;
import org.mifos.accounts.loan.persistance.StandardClientAttendanceDao;
import org.mifos.accounts.persistence.AccountPersistence;
import org.mifos.accounts.productdefinition.business.LoanProductDaoHibernate;
import org.mifos.accounts.productdefinition.persistence.LoanProductDao;
import org.mifos.accounts.savings.persistence.GenericDao;
import org.mifos.accounts.savings.persistence.GenericDaoHibernate;
import org.mifos.accounts.savings.persistence.SavingsDao;
import org.mifos.accounts.savings.persistence.SavingsDaoHibernate;
import org.mifos.accounts.savings.persistence.SavingsPersistence;
import org.mifos.application.collectionsheet.persistence.CollectionSheetDao;
import org.mifos.application.collectionsheet.persistence.CollectionSheetDaoHibernate;
import org.mifos.application.holiday.persistence.HolidayDao;
import org.mifos.application.holiday.persistence.HolidayDaoHibernate;
import org.mifos.application.master.persistence.MasterPersistence;
import org.mifos.customers.client.persistence.ClientPersistence;
import org.mifos.customers.office.persistence.OfficePersistence;
import org.mifos.customers.persistence.CustomerDao;
import org.mifos.customers.persistence.CustomerDaoHibernate;
import org.mifos.customers.persistence.CustomerPersistence;
import org.mifos.customers.personnel.persistence.PersonnelPersistence;

/**
 * I contain static factory methods for locating/creating application services.
 *
 * NOTE: Use of DI frameworks method would make this redundant. e.g.
 * spring/juice
 */
public class DependencyInjectedServiceLocator {

    // service facade
    private static CollectionSheetServiceFacade collectionSheetServiceFacade;
    private static LoanServiceFacade loanServiceFacade;

    // services
    private static CollectionSheetService collectionSheetService;

    // DAOs
    private static OfficePersistence officePersistence = new OfficePersistence();
    private static MasterPersistence masterPersistence = new MasterPersistence();
    private static PersonnelPersistence personnelPersistence = new PersonnelPersistence();
    private static CustomerPersistence customerPersistence = new CustomerPersistence();
    private static ClientPersistence clientPersistence = new ClientPersistence();
    private static SavingsPersistence savingsPersistence = new SavingsPersistence();
    private static LoanPersistence loanPersistence = new LoanPersistence();
    private static AccountPersistence accountPersistence = new AccountPersistence();
    private static ClientAttendanceDao clientAttendanceDao = new StandardClientAttendanceDao(masterPersistence);

    private static GenericDao genericDao = new GenericDaoHibernate();
    private static HolidayDao holidayDao = new HolidayDaoHibernate(genericDao);
    private static CustomerDao customerDao = new CustomerDaoHibernate(genericDao);
    private static LoanProductDao loanProductDao = new LoanProductDaoHibernate(genericDao);
    private static SavingsDao savingsDao = new SavingsDaoHibernate(genericDao);
    private static CollectionSheetDao collectionSheetDao = new CollectionSheetDaoHibernate(savingsDao);


    // translators
    private static CollectionSheetDtoTranslator collectionSheetTranslator = new CollectionSheetDtoTranslatorImpl();

    public static CollectionSheetService locateCollectionSheetService() {

        if (collectionSheetService == null) {
            collectionSheetService = new CollectionSheetServiceImpl(clientAttendanceDao, loanPersistence,
                    accountPersistence, savingsPersistence, collectionSheetDao);
        }
        return collectionSheetService;
    }

    public static CollectionSheetServiceFacade locateCollectionSheetServiceFacade() {

        if (collectionSheetServiceFacade == null) {
            collectionSheetService = DependencyInjectedServiceLocator.locateCollectionSheetService();

            collectionSheetServiceFacade = new CollectionSheetServiceFacadeWebTier(officePersistence,
                    masterPersistence, personnelPersistence, customerPersistence, collectionSheetService, collectionSheetTranslator);
        }
        return collectionSheetServiceFacade;
    }

    public static LoanServiceFacade locateLoanServiceFacade() {
        if (loanServiceFacade == null) {
            loanServiceFacade = new LoanServiceFacadeWebTier(loanProductDao, customerDao);
        }
        return loanServiceFacade;
    }

    public static CustomerDao locateCustomerDao() {
        return customerDao;
    }

    public static HolidayDao locateHolidayDao() {
        return holidayDao;
    }
}
