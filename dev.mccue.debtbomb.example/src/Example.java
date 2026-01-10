import dev.mccue.debtbomb.DebtBomb;

@DebtBomb(expiresAt = "2023-10-05", reason = "Example code is silly")
public class Example {

    @DebtBomb(expiresAt = "1000-10-10", reason = "Bad method name")
    @DebtBomb(expiresAt = "2900-10-10", reason = "Space travel!")
    @DebtBomb(expiresAt = "2000-01-01", reason = "Unix epoch", ticket = "JIRA-99999", owner = "Nobody")
    public void f() {

    }
}
