import java.util.*
import java.util.concurrent.Executors

fun main() {
    println("Bem-vindo!")

    var opcao: Int

    do {
        println("\nEscolha uma opção:")
        println("1. Fazer login e começar o monitoramento")
        println("2. Sair")

        opcao = readLine()?.toIntOrNull() ?: 0

        when (opcao) {
            1 -> fazerLoginEIniciarMonitoramento()
            2 -> println("Até logo!")
            else -> println("Opção inválida. Tente novamente.")
        }

    } while (opcao != 2)
}

fun fazerLoginEIniciarMonitoramento() {
    val scanner = Scanner(System.`in`)

    println("\nDigite seu e-mail:")
    val email = scanner.nextLine()

    println("Digite sua senha:")
    val senha = scanner.nextLine()

    // Lógica de validação no SQL Server
    val conexaoSql = Conexao.jdbcTemplateServer

    val query = """
        SELECT COUNT(*) FROM Funcionario
        WHERE email = '$email' AND senha = '$senha'
    """

    val resultado = conexaoSql!!.queryForObject(query, Int::class.java)

    if (resultado == 1) {
        println("\nLogin bem-sucedido! Esquentando as máquinas...")
        Thread.sleep(2000)

        println("Começando o monitoramento...")
        Thread.sleep(2000)

        // Executar script Python em uma thread separada
        val executor = Executors.newSingleThreadExecutor()
        executor.submit {
            val arquivoPython = scriptPadraoPython.criarScript()
            scriptPadraoPython.executarScript(arquivoPython)

            println("Monitoramento sendo bem-sucedido, muito bem!")
        }
    } else {
        println("\nLogin inválido. Tente novamente.")
    }
}