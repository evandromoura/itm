package br.com.trixti.itm.infra.security.annotations;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.deltaspike.security.api.authorization.Secures;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;

import br.com.trixti.itm.enums.PerfilEnum;

@ApplicationScoped
public class CustomAuthorizer {

	private @Inject CustomIdentity customIdentity;
    
	@Secures
	@SuperAdmin
	public boolean doSuperAdminCheck(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
		return customIdentity.getPerfil().equals(PerfilEnum.SUPER_ADMIN);
	}
	
	
	@Secures
	@Admin
	public boolean doAdminCheck(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
		return customIdentity.getPerfil().equals(PerfilEnum.ADMIN) || customIdentity.getPerfil().equals(PerfilEnum.SUPER_ADMIN);
	}
	
	@Secures
	@Cliente
	public boolean doClienteCheck(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
		return customIdentity.getPerfil().equals(PerfilEnum.CLIENTE) || customIdentity.getPerfil().equals(PerfilEnum.ADMIN) || customIdentity.getPerfil().equals(PerfilEnum.SUPER_ADMIN);
	}
	
	@Secures
	@Financeiro
	public boolean doFinanceiroCheck(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
		return customIdentity.getPerfil().equals(PerfilEnum.FINANCEIRO) || customIdentity.getPerfil().equals(PerfilEnum.ADMIN) || customIdentity.getPerfil().equals(PerfilEnum.SUPER_ADMIN);
	}
	
	@Secures
	@SuporteNivel1
	public boolean doSuporteNivel1Check(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
		return customIdentity.getPerfil().equals(PerfilEnum.SUPORTE_NIVEL1)||customIdentity.getPerfil().equals(PerfilEnum.SUPORTE_NIVEL2)||customIdentity.getPerfil().equals(PerfilEnum.ADMIN) || customIdentity.getPerfil().equals(PerfilEnum.SUPER_ADMIN);
	}
	
	@Secures
    @SuporteNivel2
    public boolean doSuporteNivel2Check(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
        return customIdentity.getPerfil().equals(PerfilEnum.SUPORTE_NIVEL2)||customIdentity.getPerfil().equals(PerfilEnum.ADMIN) || customIdentity.getPerfil().equals(PerfilEnum.SUPER_ADMIN);
    }

}
