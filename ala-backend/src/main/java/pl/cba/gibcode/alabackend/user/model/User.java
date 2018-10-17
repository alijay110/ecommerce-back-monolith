package pl.cba.gibcode.alabackend.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.cba.gibcode.alabackend.card.model.Card;
import pl.cba.gibcode.alabackend.shared.model.PriceRangeEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"cardsSell", "cardsBuy"})
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@OneToMany(
			mappedBy = "seller",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private Set<Card> cardsSell = new HashSet<>();


	@OneToMany(
			mappedBy = "buyer",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private Set<Card> cardsBuy = new HashSet<>();

	@ElementCollection(targetClass = UserType.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "user_usertype")
	@Column(name = "user_type")
	private Set<UserType> userTypes = new HashSet<>();
}
