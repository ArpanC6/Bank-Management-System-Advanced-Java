
document.addEventListener('DOMContentLoaded', function() {
    const navItems = document.querySelectorAll('.nav-item');
    const contentSections = document.querySelectorAll('.content-section');

    navItems.forEach(item => {
        item.addEventListener('click', function() {
            const targetSection = this.getAttribute('data-section');


            navItems.forEach(nav => nav.classList.remove('active'));
            contentSections.forEach(section => section.classList.remove('active'));


            this.classList.add('active');
            document.getElementById(targetSection).classList.add('active');
        });
    });


    setupFormHandlers();
});

function setupFormHandlers() {

    const savingsForm = document.querySelector('#create-savings .form');
    savingsForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = {
            name: document.getElementById('savings-name').value,
            balance: document.getElementById('savings-balance').value,
            phone: document.getElementById('savings-phone').value,
            email: document.getElementById('savings-email').value,
            address: document.getElementById('savings-address').value
        };
        console.log('Creating Savings Account:', formData);
        showMessage('Savings account creation request sent!', 'success');
        this.reset();
    });


    const currentForm = document.querySelector('#create-current .form');
    currentForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = {
            name: document.getElementById('current-name').value,
            balance: document.getElementById('current-balance').value,
            phone: document.getElementById('current-phone').value,
            email: document.getElementById('current-email').value,
            address: document.getElementById('current-address').value
        };
        console.log('Creating Current Account:', formData);
        showMessage('Current account creation request sent!', 'success');
        this.reset();
    });


    const depositForm = document.querySelector('#deposit .form');
    depositForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = {
            accountNumber: document.getElementById('deposit-account').value,
            amount: document.getElementById('deposit-amount').value
        };
        console.log('Deposit:', formData);
        showMessage('Deposit request sent!', 'success');
        this.reset();
    });


    const withdrawForm = document.querySelector('#withdraw .form');
    withdrawForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = {
            accountNumber: document.getElementById('withdraw-account').value,
            amount: document.getElementById('withdraw-amount').value
        };
        console.log('Withdraw:', formData);
        showMessage('Withdrawal request sent!', 'success');
        this.reset();
    });


    const viewAccountForm = document.querySelector('#view-account .form');
    viewAccountForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const accountNumber = document.getElementById('view-account-number').value;
        console.log('View Account:', accountNumber);
        displayAccountDetails(accountNumber);
    });


    const transactionForm = document.querySelector('#transactions .form');
    transactionForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const accountNumber = document.getElementById('transaction-account').value;
        console.log('Transaction History:', accountNumber);
        displayTransactionHistory(accountNumber);
    });


    const transferForm = document.querySelector('#transfer .form');
    transferForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = {
            fromAccount: document.getElementById('transfer-from').value,
            toAccount: document.getElementById('transfer-to').value,
            amount: document.getElementById('transfer-amount').value
        };
        console.log('Transfer:', formData);
        showMessage('Transfer request sent!', 'success');
        this.reset();
    });


    const closeForm = document.querySelector('#close-account .form');
    closeForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const accountNumber = document.getElementById('close-account-number').value;
        if (confirm('Are you sure you want to close this account? This action cannot be undone.')) {
            console.log('Close Account:', accountNumber);
            showMessage('Account closure request sent!', 'success');
            this.reset();
        }
    });
}

function showMessage(message, type) {
    const messageClass = type === 'success' ? 'success-message' : 'error-message';
    const messageDiv = document.createElement('div');
    messageDiv.className = messageClass;
    messageDiv.innerHTML = `
        <svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor">
            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"/>
        </svg>
        <span>${message}</span>
    `;

    const activeSection = document.querySelector('.content-section.active');
    activeSection.insertBefore(messageDiv, activeSection.firstChild);

    setTimeout(() => {
        messageDiv.remove();
    }, 5000);
}

function displayAccountDetails(accountNumber) {
    const resultArea = document.getElementById('account-details-result');


    const accountData = {
        accountNumber: accountNumber,
        holderName: 'Sample User',
        accountType: 'SAVINGS',
        balance: 50000.00,
        phone: '+91 98765 43210',
        email: 'user@example.com',
        status: 'ACTIVE'
    };

    resultArea.innerHTML = `
        <div class="account-card">
            <h3>Account Details</h3>
            <p><strong>Account Number:</strong> ${accountData.accountNumber}</p>
            <p><strong>Holder Name:</strong> ${accountData.holderName}</p>
            <p><strong>Account Type:</strong> ${accountData.accountType}</p>
            <p><strong>Balance:</strong> Rs. ${accountData.balance.toFixed(2)}</p>
            <p><strong>Phone:</strong> ${accountData.phone}</p>
            <p><strong>Email:</strong> ${accountData.email}</p>
            <p><strong>Status:</strong> <span style="color: #10B981; font-weight: 600;">${accountData.status}</span></p>
        </div>
    `;
}

function displayTransactionHistory(accountNumber) {
    const resultArea = document.getElementById('transaction-result');


    const transactions = [
        { type: 'DEPOSIT', amount: 5000, balance: 50000, date: '2026-01-10 10:30:00', description: 'Cash deposit' },
        { type: 'WITHDRAW', amount: 2000, balance: 45000, date: '2026-01-09 15:45:00', description: 'ATM withdrawal' },
        { type: 'TRANSFER', amount: 3000, balance: 47000, date: '2026-01-08 11:20:00', description: 'Transfer from 1001001002' }
    ];

    let html = '<h3 style="margin-bottom: 15px; color: #1F2937;">Recent Transactions</h3>';

    transactions.forEach(txn => {
        const typeClass = txn.type.toLowerCase();
        html += `
            <div class="transaction-item">
                <div>
                    <span class="transaction-type ${typeClass}">${txn.type}</span>
                    <p style="margin: 5px 0; font-size: 13px; color: #6B7280;">${txn.description}</p>
                    <p style="font-size: 12px; color: #9CA3AF;">${txn.date}</p>
                </div>
                <div style="text-align: right;">
                    <p style="font-size: 18px; font-weight: 600; color: ${txn.type === 'DEPOSIT' ? '#10B981' : '#EF4444'};">
                        ${txn.type === 'DEPOSIT' ? '+' : '-'} Rs. ${txn.amount.toFixed(2)}
                    </p>
                    <p style="font-size: 13px; color: #6B7280;">Balance: Rs. ${txn.balance.toFixed(2)}</p>
                </div>
            </div>
        `;
    });

    resultArea.innerHTML = html;
}

function loadAllAccounts() {
    const resultArea = document.getElementById('all-accounts-result');


    const accounts = [
        { accountNumber: 1001001001, holderName: 'Arpan Chakraborty', type: 'SAVINGS', balance: 50000 },
        { accountNumber: 1001001002, holderName: 'Chakraborty arpan', type: 'CURRENT', balance: 75000 },
        { accountNumber: 1001001003, holderName: 'Tubai Chakraborty', type: 'SAVINGS', balance: 30000 }
    ];

    let html = `
        <h3 style="margin-bottom: 15px; color: #1F2937;">Total Accounts: ${accounts.length}</h3>
        <table class="data-table">
            <thead>
                <tr>
                    <th>Account Number</th>
                    <th>Holder Name</th>
                    <th>Type</th>
                    <th>Balance</th>
                </tr>
            </thead>
            <tbody>
    `;

    accounts.forEach(acc => {
        html += `
            <tr>
                <td>${acc.accountNumber}</td>
                <td>${acc.holderName}</td>
                <td>${acc.type}</td>
                <td>Rs. ${acc.balance.toFixed(2)}</td>
            </tr>
        `;
    });

    html += '</tbody></table>';
    resultArea.innerHTML = html;
}

function calculateInterest() {
    const resultArea = document.getElementById('interest-result');

    const affectedAccounts = 5;
    const totalInterest = 12500;

    resultArea.innerHTML = `
        <div class="success-message">
            <svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"/>
            </svg>
            <div>
                <strong>Interest Calculated Successfully!</strong>
                <p>Affected Accounts: ${affectedAccounts} | Total Interest Added: Rs. ${totalInterest.toFixed(2)}</p>
            </div>
        </div>
    `;
}