# library
To implement a program that describes the work of a private library.

The library contains reading materials. Books can be borrowed from the library, and the books are divided by genre into separate sections. Each book has: name, author, date of issue, publishing house,genre (romance novel, thriller, ...).

The library also contains magazines and textbooks. The magazines have: name, publishing house, category (fashion, popular science, ...), issue number, date of issue.

Textbooks have: name, author, publishing house, topic (History, Programming, ...).

Textbooks are divided by subject in the library, and magazines by category. Each reading has a claim history list where you fill in:
<rental date> - <return date>.

One can browse the library catalog which is divided into three parts – textbooks, books and journals. When selecting journals, the catalog must be presented sorted by category, and for each category the journals are arranged by name and issue number.

When selecting books, they should be sorted by genre, with books displayed from the most recently published to the oldest.
When selecting textbooks, they should be arranged by subject and name in alphabetical order.
One can borrow reading material from the library. Then a rental date is filled in the rental history on its readout.
Books can be picked up for a maximum of 300 seconds, textbooks for 150 seconds, and magazines cannot be picked up - they can only be read on the spot. If a person asks to take a magazine home, the system should return a decent message that the operation is not allowed.
The fee for a book rental is BGN 2, for a textbook – BGN 3, and there is no fee for a magazine, as they can only be read in the library's reading room.
When a person borrows a book, the library keeps a record of the date the book was borrowed and the date on which the book must be returned.
For each reading taken when the rental time expires, the system must check to see if the reading was returned. If not, the system charges an interest rate of 1% of the fee for each subsequent second that the reading is not returned. This should be checked in parallel for all books borrowed from the library, and the fee should be updated in real time for each book borrowed.

When a person returns a book to the library, the librarian requires payment of the rental amount plus late interest (if any). The system should then abort the late check process so that no more interest is charged. At the end of the reading, a return date is written to the history for the last record for which a pickup date was entered.
The library is subject to revision every 31 days.
During revision in the library should be able to run
the following operations:
• To return the count of all available readings in the library
• To generate a file that lists all currently taken readings, sorted by the date they were taken, as well as their total number.
• To generate a file that lists all overdue receivables (reader name, reader name, compensation charged) and the total amount expected to be received as compensation from them. The list in the file should be ordered by the amount of compensation expected.
